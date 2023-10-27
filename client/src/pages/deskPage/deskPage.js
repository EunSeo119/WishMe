import style from "./deskPage.module.css";
import styleApp from '../../app.module.css'
import { Link } from "react-router-dom";
import React, { useState, useEffect } from 'react'
import axios from 'axios'

const DeskPage = () => {

    const [page, setPage] = useState(1)
    const [userUuid, setUserUuid] = useState(1)
    const [deskName, setDeskName] = useState('test')
    const [totalCount, setTotalCount] = useState(0)
    const [deskLetter, setDeskLetter] = useState(['https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/찹쌀떡.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/엿.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/딱풀.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/초콜릿.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/휴지.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/포크.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/찹쌀떡.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/엿.png',
        'https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/asset/desk/휴지.png'])
    // const [totalPage, setTotalPage] = useState(1)


    var url = 'http://localhost:8082/'

    useEffect(() => {
        axios
            .get(`/api/my/letter/all/${userUuid}?page=${page}`)
            .then((response) => {
                const data = response.data
                console.log(data)
                setDeskName(data.toUserNickname)
                setTotalCount(data.totalLetterCount)
                setDeskLetter(data.myLetterResponseDtoList)
                // setTotalPage(data.totalPage)
            })
            .catch((error) => {
                console.error('API 요청 중 오류 발생:', error)
            })
    }, [page])

    return (
        <div className={styleApp.app}>
            <div className={style.deskPage}>
                <img src="https://wishme-bichnali.s3.ap-northeast-2.amazonaws.com/background/deskBackground.PNG" className={styleApp.bg} />
                <div className={style.board}>
                    <div className={style.title}><b>{ }수능대박1</b>님의 책상에<br /><b>{ }246개</b>의 응원이 왔어요!</div>
                </div>
                <div className={style.desk}>

                </div>
                <div className={style.btn}>
                    <Link to="/desk/write" className={style.link}>
                        <div className={style.cheerUpBtn}>응원하기</div>
                    </Link>
                    <div className={style.myDeskBtn}>내 책상 보기</div>
                </div>
            </div>
        </div>
    )
};

export default DeskPage;