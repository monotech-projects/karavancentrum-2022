
(ns app.storage.frontend.media-viewer.views
    (:require [layouts.popup-b.api :as popup-b]
              [mid-fruits.css      :as css]
              [mid-fruits.io       :as io]
              [x.app-core.api      :as a]
              [x.app-elements.api  :as elements]
              [x.app-media.api     :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn close-icon-button
  [viewer-id]
  [elements/icon-button ::close-icon-button
                        {:color    :invert
                         :keypress {:key-code 27}
                         :on-click [:ui/close-popup! :storage.media-viewer/view]
                         :preset   :close
                         :style    {:position :fixed :right 0 :top :0}}])

;; -- PDF-item components -----------------------------------------------------
;; ----------------------------------------------------------------------------

(defn pdf-item-pdf
  [viewer-id]
  (let [% @(a/subscribe [:storage.media-viewer/get-current-item-props viewer-id])]
       [:iframe.storage--media-viewer--pdf {:src   (-> % :item-filename media/filename->media-storage-uri)
                                            :style {:border-radius (css/var  "border-radius-m")
                                                    :max-height    (css/calc "100vh - 96px")
                                                    :max-width     (css/calc "100vw - 96px")}}]))

(defn pdf-item
  [viewer-id]
  [:div.storage--media-viewer--pdf-item [pdf-item-pdf viewer-id]])

;; -- Image-item components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn image-item-icon
  [_]
  [:div.storage--media-viewer--icon {:style {:align-items "center" :display "flex" :justify-content "center"
                                             :height "100%" :left "0" :position "absolute" :top "0" :width "100%"}}
                                    [elements/icon {:icon :insert_drive_file :color :invert}]])

(defn image-item-image
  [viewer-id]
  (let [% @(a/subscribe [:storage.media-viewer/get-current-item-props viewer-id])]
       [:img.storage--media-viewer--image {:src   (-> % :item-filename media/filename->media-storage-uri)
                                           :style {:border-radius (css/var  "border-radius-m")
                                                   :max-height    (css/calc "100vh - 96px")
                                                   :max-width     (css/calc "100vw - 96px")}}]))

(defn image-item
  [viewer-id]
  [:div.storage--media-viewer--image-item {:style {:height "100%" :width "100%"}}
                                         [image-item-icon  viewer-id]
                                         [image-item-image viewer-id]])

;; -- Media-item components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn media-item
  [viewer-id]
  (let [% @(a/subscribe [:storage.media-viewer/get-current-item-props viewer-id])]
       (case (-> % :item-filename io/filename->mime-type)
             "application/pdf" [pdf-item   viewer-id]
                               [image-item viewer-id])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view-structure
  [viewer-id]
  [:<> [media-item        viewer-id]
       [close-icon-button viewer-id]])

(defn view
  [viewer-id]
  [popup-b/layout :storage.media-viewer/view
                  {:content [view-structure viewer-id]}])
