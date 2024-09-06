let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

flatpickr('input[name="reservationDay"]', {
    locale: 'ja',
    minDate: 'today',
    maxDate: maxDate
});