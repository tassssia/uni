class Student < ApplicationRecord
  include Visible

  validates :first_name, presence: true
  validates :middle_name, presence: true
  validates :last_name, presence: true
  validates :gender, presence: true
  validates :age, presence: true, numericality: { greater_than_or_equal_to: 0, only_integer: true }
  validates :course, presence: true, numericality: { greater_than: 0, less_than: 10, only_integer: true }

  def format_short
    "#{first_name[0]}.#{middle_name[0]}. #{last_name}"
  end
end
