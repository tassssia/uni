class Book
  attr_accessor :id, :name, :authors, :publisher, :year, :pages_num, :price, :binding

  def initialize(id, name, authors, publisher, year, pages_num, price, binding)
    @id = id
    @name = name
    @authors = authors
    @publisher = publisher
    @year = year
    @pages_num = pages_num
    @price = price
    @binding = binding
  end
end

def by_author(books, author)
  books.select { |book| book.authors.include?(author) }.map(&:id)
end

def by_publisher(books, publisher)
  books.select { |book| book.publisher == publisher }.map(&:id)
end

def after_year(books, year)
  books.select { |book| book.year > year }.map(&:id)
end

books = [
  Book.new(1, "The Lord of the Rings", ["J.R.R. Tolkien"], "George Allen & Unwin", 1954, 1178, 24.99, "paperback"),
  Book.new(2, "To the Lighthouse", ["Virginia Woolf"], "Hogarth Press", 1927, 182, 10.99, "hardcover"),
  Book.new(3, "The Catcher in the Rye", ["J.D. Salinger"], "Little, Brown and Company", 1951, 277, 8.99, "paperback"),
  Book.new(4, "Pride and Prejudice", ["Jane Austen"], "T. Egerton", 1813, 279, 7.99, "hardcover"),
  Book.new(5, "The Grapes of Wrath", ["John Steinbeck"], "The Viking Press", 1939, 464, 14.99, "paperback")
]
author = "Virginia Woolf"
puts "Books of #{author}: #{by_author(books, author)}"
publisher = "The Viking Press"
puts "Books of #{publisher}: #{by_publisher(books, publisher)}"
year = 1900
puts "Books published after #{year}: #{after_year(books, year)}"