
(ns app.common.frontend.surface-box.views
    (:require [app.common.frontend.surface-box.prototypes :as surface-box.prototypes]
              [mid-fruits.random                          :as random]
              [re-frame.api                               :as r]
              [x.app-components.api                       :as components]
              [x.app-elements.api                         :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- surface-box-content
  ; @param (keyword) surface-id
  ; @param (map) label-props
  ;  {:content (metamorphic-content)}
  [_ {:keys [content]}]
  [components/content content])

(defn- surface-box-label
  ; @param (keyword) surface-id
  ; @param (map) label-props
  ;  {:disabled? (boolean)(opt)
  ;   :helper (metamorphic-content)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)}
  [_ {:keys [disabled? helper info-text label]}]
  (if label (let [viewport-large? @(r/subscribe [:environment/viewport-large?])]
                 [elements/label {:content   label
                                  :disabled? disabled?
                                  :helper    helper
                                  :info-text info-text
                                  :indent    {:top :xs :vertical :s}
                                  :font-size (if viewport-large? :l :m)}])))

(defn- surface-box-icon
  ; @param (keyword) box-id
  ; @param (map) box-props
  ;  {}
  [box-id {:keys [icon icon-family] :as box-props}]
  (if icon (if-let [viewport-large? @(r/subscribe [:environment/viewport-large?])]
                   [:div {:style {:top "0" :position "absolute" :right "0" :height "100%"
                                  :display "flex" :align-items "center" :padding "0 96px"}}
                         [:i {:data-icon-family icon-family :style {:line-height "100%"
                                                                    :font-size "96px"
                                                                    :color "rgba(0, 0, 0, .03"}}
                             icon]])))

(defn- surface-box-body
  ; @param (keyword) box-id
  ; @param (map) box-props
  ;  {}
  [box-id {:keys [content overflow] :as box-props}]
  (let [viewport-small? @(r/subscribe [:environment/viewport-small?])]
       [:div {:style {:background-color "var( --fill-color )"
                      :border           "1px solid var( --border-color-highlight )"
                      :border-radius    (if viewport-small? "0" "var( --border-radius-m )")
                      :overflow         overflow}}
            ;[surface-box-icon    box-id box-props]
             [surface-box-label   box-id box-props]
             [surface-box-content box-id box-props]]))

(defn- surface-box
  ; @param (keyword) box-id
  ; @param (map) box-props
  ;  {:indent (map)(opt)}
  [box-id {:keys [indent] :as box-props}]
  [elements/blank box-id
                  {:indent  indent
                   :content [surface-box-body box-id box-props]}])

(defn element
  ; @param (keyword)(opt) box-id
  ; @param (map) box-props
  ;  {:content (metamorphic-content)
  ;   :disabled? (boolean)(opt)
  ;    Default: false
  ;   :helper (metamorphic-content)(opt)
  ;   :icon (keyword)(opt)
  ;   :icon-family (keyword)(opt)
  ;    Default: :material-icons-filled
  ;   :indent (map)(opt)
  ;   :info-text (metamorphic-content)(opt)
  ;   :label (metamorphic-content)(opt)
  ;   :overflow (keyword)(opt)
  ;    :hidden, :visible
  ;    Default: :visible}
  ;
  ; @usage
  ;  [surface-box {...}]
  ;
  ; @usage
  ;  [surface-box :my-element {...}]
  ([box-props]
   [element (random/generate-keyword) box-props])

  ([box-id box-props]
   (let [box-props (surface-box.prototypes/box-props-prototype box-props)]
        [surface-box box-id box-props])))
