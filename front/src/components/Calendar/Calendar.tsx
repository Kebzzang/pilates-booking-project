import React, { useCallback, useEffect, useState } from 'react';
import moment, { Moment } from 'moment';
import { CalendarWrapper, HR, YearMonth, YoilButton, YoilButtonNalzza } from './style';
import axios from 'axios';
import { IClasses } from '../../types/db';
import ClassComponent from './ClassComponent';

const Calendar = () => {
  const [today, setToday] = useState(moment());
  const startOfWeek = today.clone().startOf('isoWeek');

  const daysList = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
  const weekInfo: { [index: string]: moment.Moment } = {
    mon: startOfWeek,
    tue: startOfWeek.clone().add(1, 'days'),
    wed: startOfWeek.clone().add(2, 'days'),
    thu: startOfWeek.clone().add(3, 'days'),
    fri: startOfWeek.clone().add(4, 'days'),
    sat: startOfWeek.clone().add(5, 'days'),
    sun: startOfWeek.clone().add(6, 'days'),
  };

  const [pick, setPicked] = useState(today.format('ddd').toLowerCase());
  const [classes, setClasses] = useState<IClasses>({
    count: 0,
    data: [],
  });

  useEffect(() => {
    const endDate = weekInfo[pick].clone().add(1, 'days');
    axios
      .get('http://localhost:8080/api/v1/course/search', {
        params: {
          start: weekInfo[pick].format('YYYY-MM-DDT00:00:00'),
          end: endDate.format('YYYY-MM-DDT00:00:00'),
        },
      })
      .then((r) => {
        if (r.status !== 204) {
          setClasses(r.data);
        } else {
          setClasses({
            count: 0,
            data: [],
          });
          console.log('수업없어서 204뜸');
        }
      });
  }, [pick]);

  const clickedButtonHandler = (key: string) => {
    setPicked(key);
  };

  return (
    <CalendarWrapper>
      <div className="row" style={{ margin: '5px' }}>
        <div className="col mb-3 col-12 text-center">
          <YearMonth>{today.format('MMMM').toUpperCase()}</YearMonth>
          <HR />{' '}
          <div className="btn-group" role="group" aria-label="Basic example">
            {daysList.map((y) => (
              <YoilButton
                selected={pick}
                day={y}
                key={y}
                onClick={() => {
                  clickedButtonHandler(y);
                }}
              >
                <YoilButtonNalzza>{y.toUpperCase()}</YoilButtonNalzza>
                {weekInfo[y].format('DD')}
              </YoilButton>
            ))}
          </div>
        </div>

        {pick === 'mon' && <ClassComponent props={classes}>MON </ClassComponent>}
        {pick === 'tue' && <ClassComponent props={classes}>TUE </ClassComponent>}
        {pick === 'wed' && <ClassComponent props={classes}>WED </ClassComponent>}
        {pick === 'thu' && <ClassComponent props={classes}>THU </ClassComponent>}
        {pick === 'fri' && <ClassComponent props={classes}>FRI </ClassComponent>}
        {pick === 'sat' && <ClassComponent props={classes}>SAT </ClassComponent>}
        {pick === 'sun' && <ClassComponent props={classes}>SUN </ClassComponent>}
      </div>
    </CalendarWrapper>
  );
};

export default Calendar;
