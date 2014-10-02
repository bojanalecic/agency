(ns agency.routes.projects
  (:require [compojure.core :refer :all]
            [agency.views.layout :as layout]
            [hiccup.form :refer :all]
            [agency.models.db :as db]
            [hiccup.page :as hic-p]))

(defn all-projects
  []
  (let [all-projs (db/read-projects)]
     [:h1 "All Projects"]
     [:table
      [:tr [:th "Id"] [:th "Name"] [:th "Description"] [:th "Technology"]]
      (for [proj all-projs]
        [:tr [:td (:id proj)] [:td (:proj_name proj)] [:td (:description proj)] [:td (:tech_name proj)]
         [:td [:input.deleteProject {:type "submit" :value "Delete"}]]])]))

(defn add-project
  []
  (let [all-techs (db/read-technologies)]
    [:form
    [:p "Id: " [:input {:type "text" :name "id" :id "projectId"}]]
    [:p "Name: " [:input {:type "text" :name "name" :id "projectName"}]]
    [:p "Description: " [:input {:type "text" :name "description" :id "projectDescription"}]]
    [:p "Technologies:" [:select {:id "technologies"} (for [tech all-techs]
                             [:option {:id (:id tech) :value (:id tech) } (:name tech)])] ]
    [:p [:input.insertProject {:type "submit" :value "Add project"}]]]))


(defn home [& [id name error]]
  (layout/common
   [:p error]
   [:h "All projects"]
   (all-projects)
   [:hr]
   [:h "Add project"]
   (add-project)))

(defn save-project [id name description technology_id]
  (let [id (Integer/parseInt id)
        technology_id (Integer/parseInt technology_id)]
    (db/insert-project id name description technology_id)
    (home)))

(defn remove-project [id]
 (do (let [id (Integer/parseInt id)]
    (db/delete-project id))))

(defroutes project-routes
  (GET "/project" [] (home))
  (POST "/save-project" [id name description technology_id] (save-project id name description technology_id))
  (DELETE "/remove-project" [id] (remove-project id)))
