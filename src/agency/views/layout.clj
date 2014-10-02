(ns agency.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [& body]
  (html5
    [:head
     [:title "Welcome to agency"]
     (include-css "/css/screen.css")]
    [:body body
     (include-js "//code.jquery.com/jquery-1.10.1.min.js"
                 "/js/agency.js")]))
