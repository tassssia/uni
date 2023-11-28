Rails.application.routes.draw do
  root 'students#index'

  resources :students
  resources :queries do
    get :show_max_male_course,
        :show_most_common_male_name,
        :show_most_common_female_name,
        :show_females_of_most_common_age, on: :collection
    end
end
