(ns weiqi.ui
  (:require [seesaw.mouse :as mouse]
            [clojure.math.numeric-tower :as math]
            [weiqi.engine :as engine])
  (:use seesaw.core
        seesaw.graphics
        seesaw.color))

(def board-state (engine/parse-board "example.sgf"))

(def scale-factor 30)
(def board-size 19)
(def border-width 1)

(def stone-styles
 { :black (style :background (color :black))
   :white (style :background (color :white)
                 :foreground (color :black)
                 :stroke 2) })

(defn stone [[x y]]
 (circle (* scale-factor x) (* scale-factor y) (/ scale-factor 2)))

(defn paint [c g]
  (doseq [x (range 1 board-size) y (range 1 board-size)]
    (draw g
      (rect (* x scale-factor) (* y scale-factor) scale-factor)
      (style :background ( color :white )
             :foreground ( color :black )
             :stroke 2 )))

  (doseq [stone-data board-state]
    (draw g (stone (stone-data :pos)) (stone-styles (stone-data :color)))))

(def board
  (canvas :id :canvas :background "#BBBBDD" :paint paint))

(defn -main [& args]
  (invoke-later
    (-> (frame :title "Weiqi"
               :width  (+ (* scale-factor (+ board-size 1)) (* 2 border-width))
               :height (+ (* scale-factor (+ board-size 2)) (* 2 border-width))
               :content board
               :on-close :exit)
     show!)))
