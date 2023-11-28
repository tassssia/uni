class QueriesController < ApplicationController
  def show_max_male_course
    @max_male_course = max_male_course
    @students_of_course = Student.where(course: @max_male_course)
  end
  def max_male_course
    @students = Student.all
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

  def show_most_common_male_name
    @most_common_male_name = most_common_name("male")
    @male_students_with_name = Student.where(first_name: @most_common_male_name)
  end
  def show_most_common_female_name
    @most_common_female_name = most_common_name("female")
    @female_students_with_name = Student.where(first_name: @most_common_female_name)
  end
  def most_common_name(gender)
    @students = Student.all
    names = @students.select { |student| student.gender.downcase == gender.downcase }.map(&:first_name)
    res = names.max_by { |name| names.count(name) }
    res
  end

  def show_females_of_most_common_age
    @females_of_most_common_age = females_of_most_common_age
  end
  def females_of_most_common_age
    @students = Student.all
    most_common_age = @students.group_by { |student| student.age }
                               .max_by { |age, students| students.count }
                               .first

    res = @students.select { |student| student.gender.downcase == "female" && student.age == most_common_age }

    res
  end
end
