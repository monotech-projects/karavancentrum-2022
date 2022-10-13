
(ns app.storage.frontend.media-selector.subs
    (:require [mid-fruits.candy         :refer [return]]
              [mid-fruits.io            :as io]
              [mid-fruits.vector        :as vector]
              [plugins.item-browser.api :as item-browser]
              [re-frame.api             :as r :refer [r]]
              [x.app-media.api          :as media]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-selected-items
  ; @return (strings in vector)
  [db _]
  (get-in db [:storage :media-selector/selected-items]))

(defn get-selected-item-count
  ; @return (integer)
  [db _]
  (let [selected-items (r get-selected-items db)]
       (count selected-items)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn file-selected?
  [db [_ {:keys [filename]}]]
  (let [file-uri       (media/filename->media-storage-uri filename)
        selected-items (r get-selected-items db)]
       (vector/contains-item? selected-items file-uri)))

(defn file-selectable?
  [db [_ {:keys [mime-type]}]]
  (if-let [extensions (r item-browser/get-meta-item db :storage.media-selector :extensions)]
          (let [extension (io/mime-type->extension mime-type)]
               (vector/contains-item? extensions extension))
          (return true)))

(defn autosave-selected-items?
  [db [_ file-item]]
  (let [multiple? (get-in db [:storage :media-selector/selector-props :multiple?])]
       (and (not multiple?)
            (r file-selected? db file-item))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(r/reg-sub :storage.media-selector/get-selected-item-count get-selected-item-count)
(r/reg-sub :storage.media-selector/file-selected?          file-selected?)
(r/reg-sub :storage.media-selector/file-selectable?        file-selectable?)
