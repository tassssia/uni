require 'test/unit'
require 'date'
require_relative 'variant5.rb'

class TestEmail < Test::Unit::TestCase
  def setup
    @email = Email.new("Test Header", "Sender", "Recipient", Date.today)
  end

  def test_email_attributes
    assert_equal("Test Header", @email.header)
    assert_equal("Sender", @email.sender)
    assert_equal("Recipient", @email.recipient)
    assert_equal(Date.today, @email.date)
    assert_equal([], @email.tags)
    assert_nil(@email.custom_folder)
  end
end

class TestSent < Test::Unit::TestCase
  def setup
    @sent_email = Sent.new("Sent Header", "Recipient", Date.today, ["tag1", "tag2"], "Custom Folder")
  end

  def test_sent_attributes
    assert_equal("Sent Header", @sent_email.header)
    assert_equal("me", @sent_email.sender)
    assert_equal("Recipient", @sent_email.recipient)
    assert_equal(Date.today, @sent_email.date)
    assert_equal(["tag1", "tag2"], @sent_email.tags)
    assert_equal("Custom Folder", @sent_email.custom_folder)
  end
end

class TestReceived < Test::Unit::TestCase
  def setup
    @received_email = Received.new("Received Header", "Sender", Date.today, ["tag1", "tag2"], "Custom Folder")
  end

  def test_received_attributes
    assert_equal("Received Header", @received_email.header)
    assert_equal("Sender", @received_email.sender)
    assert_equal("me", @received_email.recipient)
    assert_equal(Date.today, @received_email.date)
    assert_equal(["tag1", "tag2"], @received_email.tags)
    assert_equal("Custom Folder", @received_email.custom_folder)
  end
end

class TestSpam < Test::Unit::TestCase
  def setup
    @spam_email = Spam.new("Spam Header", "Spammer", Date.today, ["spam_tag"])
  end

  def test_spam_attributes
    assert_equal("Spam Header", @spam_email.header)
    assert_equal("Spammer", @spam_email.sender)
    assert_equal("me", @spam_email.recipient)
    assert_equal(Date.today, @spam_email.date)
    assert_equal(["spam_tag"], @spam_email.tags)
    assert_equal("Spam", @spam_email.custom_folder)
  end
end

class TestMailBox < Test::Unit::TestCase
  def setup
    @email1 = Email.new("Header 1", "Sender 1", "Recipient 1", Date.today)
    @email2 = Email.new("Header 2", "Sender 2", "Recipient 2", Date.today)
    @spam_email = Spam.new("Spam Header", "Spammer", Date.today, ["spam_tag"])
    @mail_box = MailBox.new([@email1, @email2, @spam_email])
  end

  def test_sort_by_date
    sorted_emails_asc = @mail_box.sort_by_date(:asc)
    assert_equal([@spam_email, @email1, @email2], sorted_emails_asc)

    sorted_emails_desc = @mail_box.sort_by_date(:desc)
    assert_equal([@email2, @email1, @spam_email], sorted_emails_desc)
  end

  def test_folder_filter
    filtered_emails = @mail_box.folder_filter("Custom Folder")
    assert_equal([@email2], filtered_emails)
  end

  def test_sender_filter
    filtered_emails = @mail_box.sender_filter("Sender 1")
    assert_equal([@email1], filtered_emails)
  end

  def test_recipient_filter
    filtered_emails = @mail_box.recipient_filter("Recipient 1")
    assert_equal([@email1], filtered_emails)
  end

  def test_date_range_filter
    filtered_emails = @mail_box.date_range_filter(Date.today - 1, Date.today + 1)
    assert_equal([@spam_email, @email1, @email2], filtered_emails)
  end

  def test_tag_filter
    filtered_emails = @mail_box.tag_filter("spam_tag")
    assert_equal([@spam_email], filtered_emails)
  end

  def test_sender_to_spam
    @mail_box.sender_to_spam("Sender 1")
    assert_equal([@spam_email, @mail_box.black_list], [@mail_box.emails, ["Sender 1"]])
  end
end
