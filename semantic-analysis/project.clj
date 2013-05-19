(defproject delver/semantic-analysis "0.0.1" 
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lacij "0.8.1"]
                 [clojure-opennlp "0.3.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler seman.handler/app}
  :min-lein-version "2.1.3"
  :warn-on-reflection true)
