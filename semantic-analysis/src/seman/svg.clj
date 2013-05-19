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

(defn add-nodes [g nodes]
  (reduce (fn [g node] (add-node g (:id node) (name (:tag node)))) g nodes))

(defn fold-op [g edge]
  (let [src  (:from edge)
        dest (:to edge)
        id   (join-keywords src dest)]
    (add-edge g id src dest)))

(defn add-edges [g edges]
  (reduce fold-op g edges))

(defn ->svg [nodes edges stream & {:keys [w h] :or {w 800 h 600}}]
  (->
    (graph :width w :height h)
    (add-nodes nodes)
    (add-edges edges)
    (layout :hierarchical)
    (build)
    (export stream :indent "yes")))
