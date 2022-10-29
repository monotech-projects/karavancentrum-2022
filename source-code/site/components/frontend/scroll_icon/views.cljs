
(ns site.components.frontend.scroll-icon.views
    (:require [mid-fruits.random                            :as random]
              [site.components.frontend.scroll-icon.helpers :as scroll-icon.helpers]
              [site.components.frontend.scroll-sensor.views :as scroll-sensor.views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- scroll-icon
  ; @param (keyword) component-id
  ; @param (map) component-props
  ;  {:style (map)}
  [_ {:keys [style]}]
  [:<> [:div {:style {:left 0 :position :absolute :top 0}}
             [scroll-sensor.views/component ::scroll-sensor scroll-icon.helpers/scroll-f]]
       [:div {:id :sc-scroll-icon :style style}
             [:div {:id :sc-scroll-icon--body}]]])

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {:style (map)(opt)}
  ;
  ; @usage
  ;  [scroll-icon {...}]
  ;
  ; @usage
  ;  [scroll-icon :my-scroll-icon {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [scroll-icon component-id component-props]))
