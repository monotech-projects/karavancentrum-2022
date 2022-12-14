
(ns app.storage.frontend.media-selector.effects
    (:require [app.storage.frontend.media-selector.config :as media-selector.config]
              [app.storage.frontend.media-selector.events :as media-selector.events]
              [app.storage.frontend.media-selector.subs   :as media-selector.subs]
              [app.storage.frontend.media-selector.views  :as media-selector.views]
              [plugins.item-browser.api                   :as item-browser]
              [x.app-core.api                             :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :storage.media-selector/load-selector!
  ; @param (keyword)(opt) selector-id
  ; @param (map) selector-props
  ;  {:extensions (strings in vector)(opt)
  ;   :multiple? (boolean)(opt)
  ;    Default: false
  ;   :value-path (vector)}
  ;
  ; @usage
  ;  [:storage.media-selector/load-selector! {...}]
  ;
  ; @usage
  ;  [:storage.media-selector/load-selector! :my-selector {...}]
  [a/event-vector<-id]
  ; A selector-id azonosító nincs felhasználva sehol, kizárólag az *-id & *-props formula
  ; egységes használata miatt adható meg.
  (fn [{:keys [db]} [_ selector-id selector-props]]
      {:db       (r media-selector.events/load-selector! db selector-id selector-props)
       :dispatch [:storage.media-selector/render-selector!]}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :storage.media-selector/create-directory!
  (fn [{:keys [db]} [_ selected-option]]
      (let [destination-id (r item-browser/get-current-item-id db :storage.media-selector)]
           [:storage.directory-creator/load-creator! {:browser-id :storage.media-selector :destination-id destination-id}])))

(a/reg-event-fx
  :storage.media-selector/upload-files!
  (fn [{:keys [db]} [_ selected-option]]
      (let [destination-id (r item-browser/get-current-item-id db :storage.media-selector)]
           [:storage.file-uploader/load-uploader! {:browser-id :storage.media-selector :destination-id destination-id}])))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :storage.media-selector/store-selected-items!
  (fn [{:keys [db]} _]
      {:db (r media-selector.events/store-selected-items! db)}))

(a/reg-event-fx
  :storage.media-selector/save-selected-items!
  (fn [{:keys [db]} _]
      (let [db (r media-selector.events/set-saving-mode! db)]
           {:db db :dispatch-later [{:ms         media-selector.config/AUTOCLOSE-DELAY
                                     :dispatch-n [[:ui/close-popup! :storage.media-selector/view]
                                                  [:storage.media-selector/store-selected-items!]]}]})))

(a/reg-event-fx
  :storage.media-selector/file-clicked
  (fn [{:keys [db]} [_ file-item]]
      (let [db (r media-selector.events/toggle-file-selection! db file-item)]
           (if-not (r media-selector.subs/autosave-selected-items? db file-item)
                   {:db db}
                   {:db db :dispatch [:storage.media-selector/save-selected-items!]}))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :storage.media-selector/render-selector!
  (fn [_ [_ selector-id]]
      [:ui/render-popup! :storage.media-selector/view
                         {:content [media-selector.views/view selector-id]}]))
