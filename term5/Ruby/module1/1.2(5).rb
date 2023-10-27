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

books = Array.new
books << Book.new(1, "To Kill a Mockingbird", ["Harper Lee"], "J. B. Lippincott & Co.", 1960, 281, 9.99, "hardcover")
books << Book.new(2, "1984", ["George Orwell"], "Secker & Warburg", 1949, 328, 10.99, "paperback")
books << Book.new(3, "Pride and Prejudice", ["Jane Austen"], "T. Egerton", 1813, 279, 7.99, "hardcover")
books << Book.new(4, "The Catcher in the Rye", ["J.D. Salinger"], "Little, Brown and Company", 1951, 277, 8.99, "paperback")
books << Book.new(5, "Brave New World", ["Aldous Huxley"], "Chatto & Windus", 1932, 311, 11.99, "hardcover")

author = "George Orwell"
puts "Books of #{author}: #{by_author(books, author)}"
publisher = "Little, Brown and Company"
puts "Books of #{publisher}: #{by_publisher(books, publisher)}"
year = 1900
puts "Books published after #{year}: #{after_year(books, year)}"