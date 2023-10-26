import React, { useState, useEffect } from 'react'
import style from '../../app.module.css'
import axios from 'axios'
import styleSchool from './schoolPage.module.css' // CSS 모듈을 import

const SchoolPage = () => {
  const [page, setPage] = useState(1)
  const [schoolName, setSchoolName] = useState('')
  const [totalCount, setTotalCount] = useState(0)
  const [schoolLetter, setSchoolLetter] = useState([])
  const [totalPage, setTotalPage] = useState(1)

  var url = 'http://localhost:8082/'

  useEffect(() => {
    axios
      //여기 학교변경해야함,,, 어케 해야하지?
      .get(`/api/school/letter/all/1/${page}`)
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolName(data.schoolName)
        setTotalCount(data.totalCount)
        setSchoolLetter(data.schoolLetterList)
        setTotalPage(data.totalPage)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [page])

  return (
    <div className={style.app}>
      <img
        src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/schoolBackground.PNG"
        className={style.bg}
      />
      <div className={styleSchool.schoolName}>
        {schoolName}에<br></br>
        {totalCount}개의 응원이 왔어요!
      </div>

      {/* 격자 레이아웃 */}
      <div className={styleSchool.gridContainer}>
        {schoolLetter.slice(0, 12).map((letter, index) => (
          <div key={index} className={styleSchool.gridItem}>
            <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/school/1.png" />
          </div>
        ))}
      </div>
    </div>
  )
}

export default SchoolPage
