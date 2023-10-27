import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterAssetList.module.css'
import { IoIosArrowBack } from 'react-icons/io'

const SchooLetterAssetList = () => {
  const [schoolAssetList, setSchoolAssetList] = useState([])
  const { schoolId } = useParams()

  useEffect(() => {
    axios
      .get(`/api/school/letter/assets`)
      .then((response) => {
        const data = response.data
        console.log(data)
        setSchoolAssetList(data.schoolAssertList)
      })
      .catch((error) => {
        console.error('API 요청 중 오류 발생:', error)
      })
  }, [schoolId])

  return (
    <div className={style.body}>
      <div className={style.schoolName}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>문구를 선택해주세요</div>
    </div>
  )
}

export default SchooLetterAssetList
