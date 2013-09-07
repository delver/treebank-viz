(defproject delver/treebank-viz "0.0.1"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lacij "0.8.1"]
                 [clojure-opennlp "0.3.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler treebank-viz.handler/app}
  :min-lein-version "2.3.2"
  :global-vars { *warn-on-reflection* true})
