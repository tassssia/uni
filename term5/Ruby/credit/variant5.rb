require 'date'

class Email
  attr_accessor :header, :sender, :recipient, :date, :tags, :custom_folder

  def initialize(header, sender, recipient, date, custom_folder = nil, tags = [])
    @header = header
    @sender = sender
    @recipient = recipient
    @date = date
    @tags = tags
    @custom_folder = custom_folder
  end
end

class Sent < Email
  def initialize(header, recipient, date, tags = [], custom_folder = nil)
    super(header, 'me', recipient, date, tags, custom_folder)
  end
end

class Received < Email
  def initialize(header, sender, date, tags = [], custom_folder = nil)
    super(header, sender, 'me', date, tags, custom_folder)
  end
end

class Spam < Received
  def initialize(header, sender, date, tags = [])
    super(header, sender, date, tags, 'Spam')
  end
end

class MailBox
  attr_accessor :emails, :black_list

  def initialize(emails = [])
    @emails = emails
    @black_list = []
  end

  def send(email)
    @emails << Sent.new(email.header, email.recipient, email.date, email.tags)
  end

  def receive(email, folder = nil)
    if black_list.include?(sender)
      @emails << Spam.new(email.header, email.sender, email.date, email.tags)
    else
      @emails << Received.new(email.header, email.sender, email.date, email.tags, folder)
    end
  end

  def sort_by_date(order = :asc)
    sorted_emails = emails.sort_by { |email| email.date }
    sorted_emails.reverse! if order == :desc
    sorted_emails
  end

  def folder_filter(folder)
    emails.select { |email| email.custom_folder == folder }
  end
  def sender_filter(sender)
    emails.select { |email| email.sender == sender }
  end
  def recipient_filter(recipient)
    emails.select { |email| email.recipient == recipient }
  end
  def date_range_filter(date_from, date_to)
    emails.select { |email| email.date >= date_from && email.date <= date_to }
  end
  def tag_filter(tag)
    emails.select { |email| email.tags.include?(tag) }
  end

  def sender_to_spam(sender)
    senders_emails = sender_filter(sender)
    senders_emails.each { |email| to_spam(email) }
    @black_list << sender
  end

  private
  def to_spam(email)
    spam_email = Spam.new(email.header, email.sender, email.date, email.tags)
    emails[emails.index(email)] = spam_email
  end
end
