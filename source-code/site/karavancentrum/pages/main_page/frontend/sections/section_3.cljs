
(ns site.karavancentrum.pages.main-page.frontend.sections.section-3
  (:require [app.contents.frontend.api :as contents]
            [mid-fruits.href :as href]
            [re-frame.api     :as r]
            [utils.api        :refer [html->hiccup]]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn contact-information
  []
  (let [{:content/keys [body]} @(r/subscribe [:db/get-item [:site :contents :contacts-data-information]])]
       [:div.kc-contact-information (contents/parse-content-body body)]))

(defn contact-group
  [{:keys [label phone-numbers email-addresses]}]
  [:div.kc-contact-group (if label [:div.kc-contact-group--label label ":"])
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/phone-number %2)} (str %2)]))]
                                (reduce f [:<>] phone-numbers))
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/email-address %2)} (str %2)]))]
                                (reduce f [:<>] email-addresses))])

(defn contact-groups
  []
  (let [contact-groups @(r/subscribe [:db/get-item [:site :config :contact-groups]])]
       (letfn [(f [contact-groups group-props]
                  (conj contact-groups [contact-group group-props]))]
              (reduce f [:<>] contact-groups))))

(defn address-information
  []
  (let [{:content/keys [body]} @(r/subscribe [:db/get-item [:site :contents :address-data-information]])]
       [:div.kc-address-information (contents/parse-content-body body)]))

(defn address-group
  [{:keys [label company-address]}]
  (let [company-address-link (href/address company-address)]
       [:div.kc-address-group (if label [:div.kc-address-group--label label ":"])
                              [:a.kc-address-group--value {:href company-address-link :target "_blank"}
                                                          company-address]]))

(defn address-groups
  []
  (let [address-groups @(r/subscribe [:db/get-item [:site :config :address-groups]])]
       (letfn [(f [address-groups group-props]
                  (conj address-groups [address-group group-props]))]
              (reduce f [:<>] address-groups))))

(defn section-3 []
  [:<> [:section [:div#contacts [:p.kc-title "Kapcsolat"]
                                [:div#contact-groups [contact-groups]
                                                     [contact-information]
                                                     [address-groups]]
                                [:div#x

                                 [address-information]]]
                 [:section#section-3--background]]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-3])
