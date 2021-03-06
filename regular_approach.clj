;;format List
(defn code [x]
  (str (nth x 3) "\t" (nth x 1)))

;;split 
(defn split [line]
  (clojure.string/split line #";"))

;;update hashmap
(defn inc-freq [freq x]
  (if (get freq x)
    (update freq x inc)
    (assoc freq x 1)))

;;into hashmap
(defn count-freq [xs]
    (reduce inc-freq {} xs))

;;readFile 
(defn readFile [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall 
      (for [line (next (line-seq rdr))] 
      (split line) ))))

;;since 2016
(defn CSCI-fromYear [line year]
  (for [x line
        :when 
        (and 
          (< (Integer/parseInt (nth x 0)) year)
          (.contains (nth x 3) "CSCI"))  ]
    x))

;;printout new Courses since 2016
(let [file (readFile "2017-10-15.txt")
    a (set (for [line (CSCI-fromYear file 202001)]  (code line)))
    b (set (for [line (CSCI-fromYear file 201601)]  (code line)))
    c (sort (clojure.set/difference a b))] 
    (println (clojure.string/join "\n" c)))