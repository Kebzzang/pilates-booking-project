import React, { useCallback, useEffect, useState } from 'react';
import moment, { Moment } from 'moment';
import { CalendarWrapper } from './style';
import axios from 'axios';
import { IClasses } from '../../types/db';
import ClassComponent from './ClassComponent';
import useClassesSave from '../../hooks/useClassesSave';

const Calendar = () => {
  const nowTime = moment().format('YYYY-MM-DD HH:mm:ss');
  const [today, setToday] = useState(moment());
  const startOfWeek = today.clone().startOf('isoWeek');
  const MON = startOfWeek.format('YYYY-MM-DDT00:00:00');
  const TUE = startOfWeek.clone().add(1, 'days').format('YYYY-MM-DDT00:00:00');
  const WED = startOfWeek.clone().add(2, 'days').format('YYYY-MM-DDT00:00:00');
  const THU = startOfWeek.clone().add(3, 'days').format('YYYY-MM-DDT00:00:00');
  const FRI = startOfWeek.clone().add(4, 'days').format('YYYY-MM-DDT00:00:00');
  const SAT = startOfWeek.clone().add(5, 'days').format('YYYY-MM-DDT00:00:00');
  const SUN = startOfWeek.clone().add(6, 'days').format('YYYY-MM-DDT00:00:00');
  const ttt = TUE.slice(8, 10);
  console.log(1, ttt);
  console.log(TUE);
  console.log(WED);

  const mon = useClassesSave(MON, TUE);
  const tue = useClassesSave(TUE, WED);
  const wed = useClassesSave(WED, THU);
  const thu = useClassesSave(THU, FRI);
  const fri = useClassesSave(FRI, SAT);
  const sat = useClassesSave(SAT, SUN);
  const sun = useClassesSave(SUN, today.clone().endOf('isoWeek').add(1, 'days').format('YYYY-MM-DDT00:00:00'));
  console.log('mon' + mon.count);
  console.log('tue' + tue.count);
  console.log('thu' + thu.count);

  return (
    <CalendarWrapper>
      {today.format('YYYY MM')}
      <ClassComponent props={mon}>MON </ClassComponent>
      <ClassComponent props={tue}>TUE</ClassComponent>
      <ClassComponent props={wed}>WED</ClassComponent>
      <ClassComponent props={thu}>THU</ClassComponent>
      <ClassComponent props={fri}>FRI</ClassComponent>
      <ClassComponent props={sat}>SAT</ClassComponent>
      <ClassComponent props={sun}>SUN</ClassComponent>
    </CalendarWrapper>
  );
};

export default Calendar;
