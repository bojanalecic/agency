(ns agency.routes.engagements
  (:require [compojure.core :refer :all]
            [agency.views.layout :as layout]
            [hiccup.form :refer :all]
            [agency.models.db :as db]
            [hiccup.page :as hic-p]))

(defn all-engagements
  []
  (let [all-engments (db/read-engagements)]
     [:h1 "All Engagements"]
     [:table
      [:tr [:th "Id"] [:th "Description"] [:th "Employee"] [:th "Project"]]
      (for [engment all-engments]
        [:tr [:td (:id engment)] [:td (:description engment)] [:td (:emp_name engment) " "(:last_name engment)] [:td (:proj_name engment)]
         [:td [:input.deleteEngagement {:type "submit" :value "Delete"}]]])]))

(defn add-engagement
  []
  (let [all-empls (db/read-employees)
        all-projs (db/read-projects)]
    [:form
    [:p "Id: " [:input {:type "text" :name "id" :id "engagementId"}]]
    [:p "Description: " [:input {:type "text" :name "description" :id "engagementDescription"}]]
    [:p "Employees:" [:select {:id "employees"} (for [emp all-empls]
                             [:option {:id (:id emp) :value (:id emp) } (:first_name emp) " " (:last_name emp)])] ]
    [:p "Projects:" [:select {:id "projects"} (for [proj all-projs]
                             [:option {:id (:id proj) :value (:id proj) } (:proj_name proj)])] ]
    [:p [:input.insertEngagement {:type "submit" :value "Add engagement"}]]]))

(defn home [& [id name error]]
  (layout/common
   [:p error]
   [:h "All engagements"]
   (all-engagements)
   [:hr]
   [:h "Add project"]
   (add-engagement)))

(defn remove-engagement [id]
 (do (let [id (Integer/parseInt id)]
    (db/delete-engagement id))))

(defn save-engagement [id description empl_id proj_id]
  (let [id (Integer/parseInt id)
        empl_id (Integer/parseInt empl_id)
        proj_id (Integer/parseInt proj_id)]
    (db/insert-engagement id description empl_id proj_id)
    (home)))

(defroutes engagement-routes
  (GET "/engagement" [] (home))
  (POST "/save-engagement" [id description empl_id proj_id] (save-engagement id description empl_id proj_id))
  (DELETE "/remove-engagement" [id] (remove-engagement id)))
