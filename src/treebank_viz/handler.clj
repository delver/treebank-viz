(ns treebank-viz.handler
  (:use [compojure.core]
        [clojure.pprint]
        [hiccup.core]
        [ring.middleware.params :only [wrap-params]]
        [ring.util.io :only [piped-input-stream]]
        [ring.util.response :only [response content-type status]]
        [treebank-viz.svg]
        [treebank-viz.core])
  (:require [compojure.handler :as handler]))

(def no-sentence
  (-> (response "No sentence supplied") (status 400)))

(defn form []
  (html
    [:div
     [:form {:type "post" :action "/svg" }
      [:input {:type "text" :placeholder "Enter a setence" :name "q" :size 50}]
      [:input {:type "submit" :value "svg"}]]]))

(defn svg [sentence]
  (if (seq sentence)
    (let [result (analyze sentence)
          nodes  (node-finder result)
          edges  (edge-finder result)
          labels (leaf-finder result)]
      (->
        #(->svg nodes edges labels %)
        (piped-input-stream )
        (response)
        (content-type "image/svg+xml")))
    no-sentence))

(defn text [sentence]
  (if (seq sentence)
    (let [result (analyze sentence)]
      (->
        #(with-open [out (clojure.java.io/writer %)] (pprint result out))
        (piped-input-stream )
        (response)
        (content-type "text/plain")))
    no-sentence))

(defroutes app-routes
  (GET "/svg"  [:as req] (svg (get-in req [:params :q])))
  (GET "/text" [:as req] (text (get-in req [:params :q])))
  (GET "/"     [:as req] (response (form))))

(def app
  (->
    (handler/site app-routes)
    (wrap-params)))
