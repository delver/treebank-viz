(ns seman.core
  (:use [clojure.pprint] 
        [opennlp.nlp]
        [opennlp.treebank]
        [seman.tree-zipper]))

(def treebank-parser (make-treebank-parser "../../data/opennlp.sourceforge.net/models-1.5/en-parser-chunking.bin"))

(defn- add-identifiers 
  "Takes a zipper and adds a unique identifier to each map node"
  [zipper]
  (tree-edit zipper map? (fn [_ node] (assoc node :id (keyword (gensym)))))) 

(defn analyze 
  "Breaks a sentence down into a tree zipper structure"
  [sentence]
  (-> sentence vector treebank-parser first make-tree tree-zipper add-identifiers))

(defn- walk-edges [node]
  (when (map? node)
    (filter map? (:chunk node))))

(defn- make-edges [id nodes]
  (map #(hash-map :from id :to (:id %)) nodes))

(defn- edge-visitor [node state]
  {:state (concat state (make-edges (:id node) (walk-edges node)))})

(defn- node-visitor [node state]
  (when (map? node)
    {:state (conj state (select-keys node [:id :tag]))}))

(defn finder [visitor]
  (fn [node]
    (:state
      (tree-visitor (tree-zipper node) #{} [visitor]))))

(def node-finder (finder node-visitor))

(def edge-finder (finder edge-visitor))
