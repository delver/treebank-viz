(ns seman.svg
  (:use [clojure.pprint] 
        [lacij.layouts.layout]
        [lacij.edit.graph]
        [lacij.view.graphview]))

(defn- join-keywords 
  "Converts a list of keywords into a combined form, 
   e.g. :alpha :beta :gamma becomes :alpha-beta-gamma"
  [& keywords] 
  (->> keywords (map name) (clojure.string/join "-") keyword))

(defn add-nodes [g nodes & {:keys [fill-color] :or {fill-color "lightblue"}}]
  (reduce (fn [g node] (add-node g (:id node) (name (:tag node)) :style {:fill fill-color})) g nodes))

(defn add-edges [g edges]
  (reduce 
    (fn [g {:keys [from to]}]
      (let [id (join-keywords from to)]
        (add-edge g id from to)))
    g
    edges))

(defn add-labels [g labels & {:keys [fill-color] :or {fill-color "lightgreen"}}]
  (let [nodes (map #(hash-map :id (:to %) :tag (:label %)) labels)
        edges (map #(select-keys % [:from :to]) labels)]
    (->
      g
      (add-nodes nodes :fill-color fill-color)
      (add-edges edges))))

(defn ->svg [nodes edges labels stream & {:keys [w h] :or {w 800 h 600}}]
  (->
    (graph :width w :height h)
    (add-nodes nodes)
    (add-edges edges)
    (add-default-node-attrs :rx 5 :ry 5)
    (add-labels labels)
    (layout :hierarchical)
    (build)
    (export stream :indent "yes")))
