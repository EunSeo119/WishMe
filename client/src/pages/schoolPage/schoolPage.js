import React, { useState, useEffect } from 'react'
import axios from 'axios'
import styleSchool from './schoolPage.module.css' // CSS 모듈을 import
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import Slider from 'react-slick'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io' // IoIosArrowForward를 import

const SchoolPage = () => {
  const [page, setPage] = useState(1)
  const [schoolId, setSchoolId] = useState(1)
  const [schoolName, setSchoolName] = useState('')
  const [totalCount, setTotalCount] = useState(0)
  const [schoolLetter, setSchoolLetter] = useState([])
  const [totalPage, setTotalPage] = useState(1)
  const navigate = useNavigate()

  //   const settings = {
  //     dots: true,
  //     infinite: false,
  //     speed: 500,
  //     slidesToShow: 1,
  //     slidesToScroll: 1,
  //     afterChange: (current) => {
  //       changePage(current + 1)
  //     }
  //   }

  const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  const handleLetterClick = (letterId) => {
    navigate(`/schoolLetterDetail/${letterId}`)
  }

  const letterWriteClick = (schoolId) => {
    navigate(`/schoolLetterAssetList/${schoolId}`)
  }

  useEffect(() => {
    axios
      .get(`/api/school/letter/all/${schoolId}/${page}`)
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
    <div>
      <div className={styleSchool.main}>
        <img
          src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/schoolBackground.PNG"
          className={styleSchool.bg}
        />
        <div className={styleSchool.schoolName}>
          <b>{schoolName}</b>에
          <b>
            <br></br>
            {totalCount}
          </b>
          개의 응원이 왔어요!
        </div>

        <div className={styleSchool.gridContainer}>
          <div
            className={`${styleSchool.arrowIcon} ${
              page === 1 ? styleSchool.disabledArrow : ''
            }`}
            onClick={() => {
              if (page > 1) {
                changePage(page - 1)
              }
            }}
          >
            <IoIosArrowBack />
          </div>
          <div className={styleSchool.gridItemContainer}>
            {schoolLetter.slice(0, 12).map((letter, index) => (
              <div
                key={index}
                className={styleSchool.gridItem}
                onClick={() => handleLetterClick(letter.schoolLetterSeq)}
              >
                <img src={`${letter.assetImg}`} />
              </div>
            ))}
          </div>
          <div
            className={`${styleSchool.arrowIcon} ${
              page === totalPage ? styleSchool.disabledArrow : ''
            }`}
            onClick={() => changePage(page + 1)}
          >
            <IoIosArrowForward />
          </div>
        </div>
        {/* 여기가 끝 */}
      </div>
      <div className={styleSchool.btn}>
        <div
          className={styleSchool.mySchoolBtn}
          onClick={() => letterWriteClick(schoolId)}
        >
          응원하기
        </div>
        <div className={styleSchool.mySchoolBtn}>내 책상 보기</div>
      </div>
    </div>
  )
}

export default SchoolPage
