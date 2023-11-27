class AddStatusToStudents < ActiveRecord::Migration[7.0]
  def change
    add_column :students, :status, :string
  end
end
