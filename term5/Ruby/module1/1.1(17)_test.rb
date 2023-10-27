require "test/unit"
require_relative "1.1(17)"

class TestTask1 < Test::Unit::TestCase

  def test1
    result = F(-15, 2, 4, -1)
    assert_equal 439, result
  end

  def test2
    result = F(5, 2, 0, 1)
    assert_equal -5, result
  end

  def test3
    result = F(-5, 2, 0, 1)
    assert_equal 5, result
  end

  def test4
    result = F(0, 5, 0, 2)
    assert_equal 0, result
  end

  def test5
    result = F(10, 5, 3, 1.0)
    assert_equal -2.5, result
  end
end