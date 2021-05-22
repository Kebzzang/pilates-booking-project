import React, { useState } from 'react';
import moment from 'moment';

const calc = (value: moment.Moment) => {
  const startOfWeek2 = value.clone().startOf('isoWeek');
  const endOfWeek2 = startOfWeek2.clone().add(13, 'days');
  const _day = startOfWeek2.clone().subtract(1, 'day');

  const calendar: moment.Moment[] = [];
  while (_day.isBefore(endOfWeek2, 'day')) {
    // calendar.push(
    //   Array(7)
    //     .fill(0)
    //     .map(() => _day.add(1, 'day').clone()),
    // );
    calendar.push(_day.add(1, 'day').clone());
  }
  return calendar;
};

export default calc;
