require "test/unit"
require_relative "1.2(5)"

class TestTask2< Test::Unit::TestCase

  def setup
    @books = [
      Book.new(1, "The Lord of the Rings", ["J.R.R. Tolkien"], "George Allen & Unwin", 1954, 1178, 24.99, "paperback"),
      Book.new(2, "To the Lighthouse", ["Virginia Woolf"], "Hogarth Press", 1927, 182, 10.99, "hardcover"),
      Book.new(3, "The Catcher in the Rye", ["J.D. Salinger"], "Little, Brown and Company", 1951, 277, 8.99, "paperback"),
      Book.new(4, "Pride and Prejudice", ["Jane Austen"], "T. Egerton", 1813, 279, 7.99, "hardcover"),
      Book.new(5, "The Grapes of Wrath", ["John Steinbeck"], "The Viking Press", 1939, 464, 14.99, "paperback")
    ]
  end

  def test_by_author1
    result = by_author(@books, "Virginia Woolf")
    assert_equal [2], result
  end

  def test_by_author2
    result = by_author(@books, "Vasyl Stus")
    assert_equal [], result
  end

  def tets_by_publisher1
    result = by_publisher(@books, "The Viking Press")
    assert_equal [5], result
  end

  def test_by_publisher2
    result = by_publisher(@books, "Vihola")
    assert_equal [], result
  end

  def tets_after_year1
    result = after_year(@books, 1900)
    assert_equal [1, 2, 3, 5], result
  end

  def test_after_year2
    result = after_year(@books, 2000)
    assert_equal [], result
  end
end