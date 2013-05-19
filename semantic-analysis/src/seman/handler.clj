(ns seman.handler
  (:use [compojure.core]
        [clojure.pprint]
        [hiccup.middleware :only [wrap-base-url]]
        [ring.middleware.params :only [wrap-params]] 
        [ring.util.io :only [piped-input-stream]]
        [ring.util.response :only [response]]
        [seman.svg]
        [seman.core]
        )
  (:require [compojure.handler :as handler]))

(defn process-request [sentence]
  (let [result (analyze sentence)
        nodes  (node-finder result)
        edges  (edge-finder result)]
  (pprint result)
  (response 
    (piped-input-stream
          (fn [out]
            (->svg nodes edges out)))))) 

(defroutes app-routes
  (GET "/" [:as req]
    (if-let [sentence (get-in req [:params :q])]
      (process-request sentence)
      (response "Add a query string to the URL, e.g. ?q=Why did the chicken cross the road ?")))) 

(def app 
  (-> 
    (handler/site app-routes)
    (wrap-base-url)
    (wrap-params)))
