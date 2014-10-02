(ns agency.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [agency.routes.home :refer [home-routes]]
            [agency.routes.employees :refer [employee-routes]]
            [agency.routes.projects :refer [project-routes]]
            [agency.routes.engagements :refer [engagement-routes]]))

(defn init []
  (println "agency is starting"))

(defn destroy []
  (println "agency is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes engagement-routes project-routes employee-routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))


