(ns seman.handler
  (:use [compojure.core]
        [clojure.pprint]
        [hiccup.middleware :only [wrap-base-url]]
        [ring.middleware.params :only [wrap-params]] 
        [ring.util.io :only [piped-input-stream]]
        [ring.util.response :only [response content-type status]]
        [seman.svg]
        [seman.core]
        )
  (:require [compojure.handler :as handler])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream OutputStreamWriter]))

(defn- create-pipe [f pipe-size]
  (with-open [out-stream (ByteArrayOutputStream. pipe-size)]
    (f out-stream)
    (ByteArrayInputStream. (.toByteArray out-stream))))


(defn svg [sentence]
  (if (seq sentence)
    (let [result (analyze sentence)
          nodes  (node-finder result)
          edges  (edge-finder result)
          labels (leaf-finder result)] 
      (->
        (piped-input-stream #(->svg nodes edges labels %))
        (response)
        (content-type "image/svg+xml")))
    (-> 
      (response "No sentence supplied") 
      (status 400)))) 

(defn text [sentence]
  (if (seq sentence)
    (let [result (analyze sentence)]
      (->
        ; Couldn't get this working with piped-input-stream ...
        (create-pipe #(pprint result (OutputStreamWriter. %)) 0x10000)
        (response)
        (content-type "text/plain"))) 
    (-> 
      (response "No sentence supplied") 
      (status 400)))) 

(defroutes app-routes
  (GET "/svg" [:as req] (svg (get-in req [:params :q])))
  (GET "/text" [:as req] (text (get-in req [:params :q]))))

(def app 
  (-> 
    (handler/site app-routes)
    (wrap-base-url)
    (wrap-params)))
