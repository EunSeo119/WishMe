import React, { useState, useRef, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import style from './schoolLetterWritePage.module.css'
import { useNavigate } from 'react-router-dom'
import { IoIosArrowBack, IoIosArrowForward } from 'react-icons/io'

const SchooLetterWritePage = () => {
  const [schoolAssetList, setSchoolAssetList] = useState([])
  const [page, setPage] = useState(1)
  const [totalPage, setTotalPage] = useState(1)
  const [selectAsset, setSelectAsset] = useState(1)
  const [nickname, setNickname] = useState('')
  const canvasRef = useRef(null) // Ref를 생성하여 Canvas 엘리먼트에 접근

  const { assetId } = useParams()
  const navigate = useNavigate()

  const drawTextOnCanvas = () => {
    const canvas = canvasRef.current
    const ctx = canvas.getContext('2d')

    // Clear the canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height)

    // Style for the text
    ctx.font = '24px Arial'
    ctx.fillStyle = 'black'

    // Get the user's input
    const text = nickname

    // Calculate the position for the text
    const x = 50 // X-coordinate
    const y = 50 // Y-coordinate

    // Draw the text on the canvas
    ctx.fillText(text, x, y)
  }

  const assetClick = (assetId) => {
    setSelectAsset(assetId)
  }

  const letterWriteNextClick = () => {
    navigate(`/schoolLetterDetail/${selectAsset}`)
  }
  const goPre = (assetId) => {
    setSelectAsset(assetId)
  }

  const changePage = (newPage) => {
    console.log(totalPage)
    if (newPage >= 1 && newPage <= totalPage) {
      setPage(newPage)
    }
  }

  const handleNicknameChange = (event) => {
    // 4. 상태 업데이트
    setNickname(event.target.value)
  }

  //   useEffect(() => {
  //     axios
  //       .get(`/api/school/letter/assets`)
  //       .then((response) => {
  //         const data = response.data
  //         console.log(data)
  //         setSchoolAssetList(data.schoolAssertList)
  //         setTotalPage(Math.ceil(data.schoolAssertList.length / 12))
  //         console.log(schoolAssetList)
  //         console.log(totalPage)
  //       })
  //       .catch((error) => {
  //         console.error('API 요청 중 오류 발생:', error)
  //       })
  //   }, [assetId])

  return (
    <div className={style.body}>
      <div className={style.schoolName} onClick={() => goPre()}>
        <IoIosArrowBack className={style.icon} />
        이전으로
      </div>
      <div className={style.title}>응원의 말을 남겨주세요!</div>
      <div className={style.nickname}>
        <input
          className={style.nicknameInput}
          type="text"
          id="nickname"
          value={nickname}
          placeholder="닉네임을 입력해주세요."
          onChange={handleNicknameChange}
        />
      </div>
      <div className={style.letterImg}>
        <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/letter/clovaLetter.png" />
        <canvas ref={canvasRef} id="text-canvas" className={style.canvas} />
      </div>
      <div className={style.btn}>
        <div className={style.mySchoolBtn} onClick={() => drawTextOnCanvas()}>
          다음
        </div>
      </div>
    </div>
  )
}

export default SchooLetterWritePage
