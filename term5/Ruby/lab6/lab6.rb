class Student
  attr_accessor :first_name, :middle_name, :last_name, :gender, :age, :course

  def initialize(first_name, middle_name, last_name, gender, age, course)
    @first_name = first_name
    @middle_name = middle_name
    @last_name = last_name
    @gender = gender
    @age = age
    @course = course
  end

  def print
    puts "#{@first_name} #{@middle_name} #{@last_name} (#{@gender}, #{@age}) - #{@course} course"
  end
end

class StudentData

  def initialize(students)
    @students = students
  end

  def add(student)
    @students << student
  end

  def remove(student)
    @students.delete(student)
  end

  def max_male_course
    counts = Hash.new { |hash, course| hash[course] = { male: 0, female: 0 } }

    @students.each do |student|
      counts[student.course][:male] += 1 if student.gender.downcase == "male"
      counts[student.course][:female] += 1 if student.gender.downcase == "female"
    end

    max = 0
    res = nil

    counts.each do |course, gender|
      total = gender[:male] + gender[:female]
      m_percent = (gender[:male].to_f / total) * 100

      if m_percent > max
        max = m_percent
        res = course
      end
    end

    res
  end

  def most_common_name(gender)
    names = @students.select { |student| student.gender.downcase == gender.downcase }.map(&:first_name)
    res = names.max_by { |name| names.count(name) }
    res
  end

  def females_of_most_common_age
    most_common_age = @students.group_by { |student| student.age }
                              .max_by { |age, students| students.count }
                              .first

    res = @students.select { |student| student.gender.downcase == "female" && student.age == most_common_age }
                  .map { |student| "#{student.first_name[0]}.#{student.middle_name[0]}. #{student.last_name}" }
                  .sort

    res
  end

  def print
    @students.each do
      |student| student.print
    end
  end
end

students_data = StudentData.new([
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

puts "Course with maximum percentage of male students: #{students_data.max_male_course}"
puts "The most common male name is #{students_data.most_common_name("male")}"
puts "The most common female name is #{students_data.most_common_name("female")}"
puts "Female students of the most common age: #{students_data.females_of_most_common_age}"
