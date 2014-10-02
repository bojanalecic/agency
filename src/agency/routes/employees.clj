(ns agency.routes.employees
  (:require [compojure.core :refer :all]
            [agency.views.layout :as layout]
            [hiccup.form :refer :all]
            [agency.models.db :as db]
            [hiccup.page :as hic-p]))

(defn all-employees
  []
  (let [all-emp (db/read-employees)]
     [:h1 "All employees"]
     [:table
      [:tr [:th "Id"] [:th "First Name"] [:th "Last Name"] [:th "School"] [:th "Delete"] [:th "Update"]]
      (for [emp all-emp]
      [:tr
         [:td (:id emp)]
         [:td.editable (:first_name emp)]
         [:td.editable (:last_name emp)]
         [:td.editable (:school emp)]
         [:td [:input.deleteEmployee {:type "submit" :value "Delete"}]]
         [:td [:input.updateEmployee {:type "submit" :value "Update"}]]])]))

(defn add-employee []
  [:h1 "Add a employee"]
  [:form {:action "/employee" :method "POST"}
   [:p "Id value: " [:input {:type "text" :name "id"}]]
   [:p "First Name value: " [:input {:type "text" :name "first-name"}]]
   [:p "Last Name value: " [:input {:type "text" :name "last-name"}]]
   [:p "School: " [:input {:type "text" :name "school"}]]
   [:p [:input {:type "submit" :value "Add employee"}]]])

(defn home [& [id name error]]
  (layout/common
   [:p error]
   (all-employees)
   [:hr]
   (add-employee)))

(defn save-employee [id first-name last-name school]
(cond
(empty? id)
(home id name "Mandatory field.")
:else
(do
  (let [id (Integer/parseInt id)]
    (db/insert-employee id first-name last-name school)
    (home)))))

(defn remove-employee [id]
 (do (let [id (Integer/parseInt id)]
    (db/delete-employee id)
    )))

(defn update-employee [id first-name last-name school]
  (do (let [id (Integer/parseInt id)]
    (db/update-employee id first-name last-name school))))

(defroutes employee-routes
  (GET "/employee" [] (home))
  (POST "/employee" [id first-name last-name school] (save-employee id first-name last-name school))
  (DELETE "/remove-employee" [id] (remove-employee id))
  (POST "/update-employee" [id first_name last_name school] (update-employee id first_name last_name school)))





