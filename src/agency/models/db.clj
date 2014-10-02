(ns agency.models.db
(:require [clojure.java.jdbc :as sql])
(:import java.sql.DriverManager))

(def db
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :subname "//localhost:5432/agency"
     :username "bojana"
     :password "bojana"})

;;employee
(defn insert-employee [id first-name last-name school]
  (sql/insert! db :employee
                {:id id
                 :first_name first-name
                 :last_name last-name
                 :school school}))

(defn update-employee [id first-name last-name school]
  (sql/update! db :employee
               {:first_name first-name
                :last_name last-name
                :school school}
               ["id = ?" id]))

(defn delete-employee [id]
 (sql/delete! db :employee ["id = ?" id]))

(defn read-employees []
 (sql/query db
 ["select * from employee order by id"]))

;;techology
(defn read-technologies []
 (sql/query db
 ["select * from technology order by id"]))

;;project
(defn delete-project [id]
 (sql/delete! db :project ["id = ?" id]))

(defn insert-project [id name description technology_id]
  (sql/insert! db :project
                {:id id
                 :name name
                 :description description
                 :technology_id technology_id}))

(defn read-projects []
  (sql/query db
   ["select p.id, p.name as proj_name, p.description, t.name as tech_name from project p inner join technology t on p.technology_id=t.id"]))

;;engagement

(defn delete-engagement [id]
 (sql/delete! db :engagement ["id = ?" id]))

(defn insert-engagement [id description empl_id proj_id]
  (sql/insert! db :engagement
                {:id id
                 :description description
                 :empl_id empl_id
                 :proj_id proj_id}))


(defn read-engagements []
  (sql/query db
   ["select e.id, e.description, p.name as proj_name, emp.first_name as emp_name, emp.last_name from (engagement e inner join project p on e.proj_id=p.id) inner join employee emp on e.empl_id = emp.id"]))


























