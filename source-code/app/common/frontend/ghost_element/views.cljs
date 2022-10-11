
(ns app.common.frontend.ghost-element.views
    (:require [app.common.frontend.ghost-element.prototypes :as ghost-element.prototypes]
              [mid-fruits.random                            :as random]
              [x.app-elements.api                           :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- surface-box-ghost
  ; @param (keyword) element-id
  ; @param (map) element-props
  ;  {}
  [element-id {:keys [] :as element-props}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- ghost-element
  ; @param (keyword) element-id
  ; @param (map) element-props
  ;  {:layout (keyword)}
  [element-id {:keys [layout] :as element-props}]
  (case layout :surface-box [surface-box-ghost element-id element-props]))

(defn element
  ; @param (keyword)(opt) element-id
  ; @param (map) element-props
  ;  {:layout (keyword)
  ;    :surface-box}
  ;
  ; @usage
  ;  [common/ghost-element {...}]
  ;
  ; @usage
  ;  [common/ghost-element :my-element {...}]
  ([element-props]
   [element (random/generate-keyword) element-props])

  ([element-id element-props]
   (let [element-props (ghost-element.prototypes/element-props-prototype element-props)]
        [ghost-element element-id element-props])))