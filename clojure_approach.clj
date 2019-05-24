(require '[clojure.java.io :as io])
(require 'clojure.set)

(defn lines []
  (let [rdr (io/reader "2017-10-15.txt")]
    (line-seq rdr)))

(defn parse-line [line]
  (let [parts (clojure.string/split line #";")
        [semester title crn 
         code levels cred 
         campus sec cap 
         actual starthour startmin 
         endhour endmin wk rm date-start date-end schedtype inst prereq coreq] parts]
    {:semester semester
     :title title
     :code code
     :subj (subs code 0 4)}))

(defn is-csci? [entry] (= (:subj entry) "CSCI"))

(defn get-course-codes [entries upper-bound]
  (let [keep-entries (filter (fn [entry] 
                               (< (compare (:semester entry) upper-bound) 0)) entries)
        codes (map :code keep-entries)]
    (into #{} codes)))


(let [csci-entries (filter is-csci? (map parse-line (lines)))
      all-courses (get-course-codes csci-entries "2020")
      old-courses (get-course-codes csci-entries "2016")
      new-courses (clojure.set/difference all-courses old-courses)]
  (doseq [c (sort new-courses)]
    (println c)))