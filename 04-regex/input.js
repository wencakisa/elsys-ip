regExps = {
  'exercise_1': /[A-Z][a-z]+/,
  'exercise_2': /088[1-9]\d{6}/,
  'exercise_3': /[^01]+/,
  // Using \w regex metacharacter
  // which represents the following symbols at once:
  // [a-zA-Z_0-9]
  // and it fits perfect for the use case.
  'exercise_4': /^[a-zA-Z][\w.]{2,13}$/,
  'exercise_5': /^(\d{1,3}|1[0-4]\d{2}|1500)$/,
  // According to HTML class name specification:
  // Naming rules:
  // -> Must begin with a letter A-Z or a-z
  // -> Can be followed by:
  //    -> letters (A-Za-z),
  //    -> digits (0-9)
  //    -> hyphens ("-")
  //    -> and underscores ("_")
  'exercise_6': /class=['"][a-zA-Z][\w- ]+['"]/
};

cssSelectors = {
  'exercise_1': 'css > item > java',
  'exercise_2': 'css > different > different > java',
  'exercise_3': 'css > item > java > tag',
  'exercise_4': 'css > item#someId + item',
  'exercise_5': 'css > item > tag > java + java.class1',
  'exercise_6': 'css > item#someId > item > item > item > item',
  'exercise_7': 'css > different > different#diffId2 > java + java.diffClass',
  'exercise_8': '#someId'
};
