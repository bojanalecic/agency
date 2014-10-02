(ns agency.routes.home
  (:require [compojure.core :refer :all]
            [agency.views.layout :as layout]))


(defn home []
(layout/common
 [:h1 "Admin page"]
 [:p "Please choose:"]
 [:input {:type "a" :value "Engagements" :onclick "window.location.href = '/engagement'"}][:br]
 [:input {:type "a" :value "Projects" :onclick "window.location.href = '/project'"}] [:br]
 [:input {:type "a" :value "Empolyees" :onclick "window.location.href = '/employee'"}]))

(defroutes home-routes
  (GET "/" [] (home)))
