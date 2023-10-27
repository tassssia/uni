require "test/unit"
require_relative "lab6.rb"

class TestTask2< Test::Unit::TestCase

  def setup
    @students_data = StudentData.new([
                                      Student.new("John", "Robert", "Doe", "male", 20, 1),
                                      Student.new("Jane", "Ann", "Smith", "female", 22, 1),
                                      Student.new("Mike", "William", "Johnson", "male", 21, 2),
                                      Student.new("Emily", "Marie", "Davis", "female", 20, 2),
                                      Student.new("David", "James", "Wilson", "male", 22, 2),
                                      Student.new("Michael", "Andrew", "Brown", "male", 23, 3),
                                      Student.new("Mary", "Elizabeth", "Jones", "female", 22, 3),
                                      Student.new("Sarah", "Jane", "Johnson", "female", 20, 3),
                                      Student.new("Daniel", "Thomas", "Miller", "male", 24, 4),
                                      Student.new("Olivia", "Sophia", "Davis", "female", 21, 4),
                                      Student.new("Sophia", "Ava", "Williams", "female", 23, 4),
                                      Student.new("Liam", "Lucas", "Brown", "male", 24, 5),
                                      Student.new("Ella", "Mia", "Garcia", "female", 20, 5),
                                      Student.new("Matthew", "Benjamin", "Martinez", "male", 22, 5),
                                      Student.new("Chloe", "Zoe", "Jackson", "female", 23, 6),
                                      Student.new("Ethan", "Alexander", "White", "male", 20, 6),
                                      Student.new("Aiden", "William", "Taylor", "male", 21, 6),
                                      Student.new("Mia", "Abigail", "Johnson", "female", 22, 6),
                                      Student.new("Sophia", "Olivia", "Smith", "female", 21, 6),
                                      Student.new("William", "Elijah", "Anderson", "male", 24, 6)
                                    ])
  end

  def test_max_male_course
    result = @students_data.max_male_course
    assert_equal 2, result
  end

  def test_common_name_male
    result = @students_data.most_common_name("male")
    assert_equal "John", result
  end

  def test_common_name_female
    result = @students_data.most_common_name("female")
    assert_equal "Sophia", result
  end

  def test_females_of_most_common_age
    result = @students_data.females_of_most_common_age
    assert_equal ["E.M. Davis", "E.M. Garcia", "S.J. Johnson"], result
  end
end