class CreateStudents < ActiveRecord::Migration[7.0]
  def change
    create_table :students do |t|
      t.string :first_name
      t.string :middle_name
      t.string :last_name
      t.string :gender
      t.integer :age
      t.integer :course

      t.timestamps
    end
  end
end
