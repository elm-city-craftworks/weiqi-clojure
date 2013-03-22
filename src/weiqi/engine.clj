(ns weiqi.engine)

(def black-pattern #"AB((\[\w\w\])+)")
(def white-pattern #"AW((\[\w\w\])+)")
(def stone-pattern #"\[(\w\w)\]") 

(defn sgf-to-xy [coord]
  (let [[x y] (map int coord)]
    { :pos [(- x 97) (- y 97)] }))

(defn parse-stones [sgf-data color-pattern label]
  (map #(assoc (sgf-to-xy (second %)) :color label)
        (re-seq stone-pattern (second (re-find color-pattern sgf-data)))))

(defn parse-board [filename]
  (let [sgf-data (slurp filename)]
    (lazy-cat (parse-stones sgf-data black-pattern :black)
              (parse-stones sgf-data white-pattern :white))))
