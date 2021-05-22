import React, { ChangeEvent, useEffect, useState } from 'react';
import moment from 'moment';
import calc from './calc';
import { CalendarWrapper, HR, DayPicker, YearMonth, DateInfo } from './style';

function DatePicker({ value, onChange }: { value: moment.Moment; onChange: any }) {
  const today = moment();
  const [allowNextWeek, setAllowNextWeek] = useState(0);
  const [calendar, setCalendar] = useState<moment.Moment[]>([]);
  useEffect(() => {
    setCalendar(calc(value));
    if (today.weekday() <= 6) {
      setAllowNextWeek(1);
    }
    console.log(value);
  }, []);

  function isSelected(day: moment.Moment) {
    return value.isSame(day, 'day');
  }
  function beforeToday(day: moment.Moment) {
    return moment(day).isBefore(new Date(), 'day');
  }

  function dayStyles(day: moment.Moment) {
    if (beforeToday(day)) {
      return 'before';
    }
    if (isSelected(day)) {
      return 'selected';
    }
    return 'noStyle';
  }

  return (
    <div className="row" style={{ margin: '5px' }}>
      <div className="col mb-0 col-12">
        <YearMonth>{value.format('MMMM').toUpperCase()}</YearMonth>
        <HR />
        <div className="container">
          <div className="items">
            {!allowNextWeek //토요일 일요일이면 이제 두 주 것 반환 가능.
              ? calendar.slice(7).map((date) => (
                  <DayPicker
                    key={date.toString()}
                    dayStyle={dayStyles(date)}
                    disabled={dayStyles(date) === 'before'}
                    onClick={() => {
                      onChange(date);
                    }}
                  >
                    <DateInfo>{date.format('ddd').toUpperCase()}</DateInfo>
                    {date.format('DD')}
                  </DayPicker>
                ))
              : calendar.map((date) => (
                  <DayPicker
                    key={date.toString()}
                    disabled={dayStyles(date) === 'before'}
                    onClick={() => onChange(date)}
                    dayStyle={dayStyles(date).toString()}
                  >
                    <DateInfo>{date.format('ddd').toUpperCase()}</DateInfo>
                    {date.format('DD')}
                  </DayPicker>
                ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default DatePicker;
