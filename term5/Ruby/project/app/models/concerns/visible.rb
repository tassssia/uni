module Visible
  extend ActiveSupport::Concern

  VALID_STATUSES = ['enrolled', 'on academic leave']

  included do
    validates :status, inclusion: { in: VALID_STATUSES }
  end

  class_methods do
    def enrolled_count
      where(status: 'enrolled').count
    end

    def academ_leave_count
      where(status: 'on academic leave').count
    end
  end

  def academ_leave?
    status == 'on academic leave'
  end
end