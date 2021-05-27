import React, { ChangeEvent, useCallback, useEffect, useState } from 'react';
import moment from 'moment';
import calc from './calc';
import { HR, DayPicker, YearMonth, DateInfo } from './style';
//value 선택한 날짜
function DatePicker({ value, onChange }: { value: moment.Moment; onChange: any }) {
  const today = moment();
  const [allowNextWeek, setAllowNextWeek] = useState(true);
  const [calendar, setCalendar] = useState<moment.Moment[]>([]);
  useEffect(() => {
    console.log(value);
    setCalendar(calc(value));
    if (today.weekday() <= 6) {
      setAllowNextWeek(false);
    }
    console.log('오늘', today.weekday());
  }, []);

  const dayStyles = useCallback(
    (day: moment.Moment) => {
      if (day.isBefore(new Date(), 'day')) {
        return 'before';
      }

      if (value.isSame(day, 'day')) {
        return 'selected';
      }
      return 'noStyle';
    },
    [value],
  );

  return (
    <div className="row" style={{ margin: '5px' }}>
      <div className="col mb-0 col-12">
        <YearMonth>{value.format('MMMM').toUpperCase()}</YearMonth>
        <HR />
        <div className="container">
          <div className="items">
            {!allowNextWeek //토요일 일요일이면 이제 두 주 것 반환 가능.
              ? calendar.slice(0, 7).map((date) => (
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
