
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.brands
    (:require [css.api        :as css]
              [mid-fruits.uri :as uri]
              [re-frame.api   :as r]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn brand
  [brand-dex {:keys [link-label link description title]}]
  [:div.kc-brand [:p.kc-brand--title         title]
                 [:div.kc-brand--description description]
                 [:div {:class :kc-brand--link-box}
                       [:div {}
                             [:div.kc-brand--label link-label]
                             [:div.kc-brand--link  link]]
                       [:a.kc-brand--goto {:href (uri/valid-uri link) :title "Hivatkozás megnyitása" :target "_blank"}
                                          [:span "Megtekintés"]
                                          [:i.fas.fa-arrow-right]]]])

(defn brands
  []
  (let [brands @(r/subscribe [:db/get-item [:site :content :brands]])]
       [:div#kc-brands [:div [:p.kc-section-title "Értékesítés"]]
                       [:div#kc-brands--brand-list (letfn [(f [%1 %2 %3] (conj %1 [brand %2 %3]))]
                                                          (reduce-kv f [:<>] brands))]]))

(defn view
  []
  [:section {:id :brands}
            [brands]])