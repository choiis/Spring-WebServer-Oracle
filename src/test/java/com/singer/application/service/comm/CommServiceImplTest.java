package com.singer.application.service.comm;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.dao.CommDao;
import com.singer.domain.entity.CommEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommServiceImplTest {

    @Mock
    private CommDao commDao;

    @InjectMocks
    private CommServiceImpl commService;

    private static final String TEST_USER = "testuser";

    @Nested
    @DisplayName("selectCode() 메서드 테스트")
    class SelectCodeTest {

        @Test
        @DisplayName("코드 조회 성공")
        void selectCode_success() throws Exception {
            // given
            CommEntity vo = new CommEntity();
            vo.setCodegrp("GRP01");

            CommEntity result1 = new CommEntity();
            result1.setCodegrp("GRP01");
            result1.setCodecd("001");
            result1.setCodenm("코드1");

            CommEntity result2 = new CommEntity();
            result2.setCodegrp("GRP01");
            result2.setCodecd("002");
            result2.setCodenm("코드2");

            List<CommEntity> list = Arrays.asList(result1, result2);

            when(commDao.selectCode(any(CommEntity.class))).thenReturn(list);

            // when
            List<CommEntity> result = commService.selectCode(vo);

            // then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(commDao).selectCode(vo);
        }

        @Test
        @DisplayName("코드 조회 - 빈 결과")
        void selectCode_emptyResult() throws Exception {
            // given
            CommEntity vo = new CommEntity();
            vo.setCodegrp("INVALID");

            when(commDao.selectCode(any(CommEntity.class))).thenReturn(Collections.emptyList());

            // when
            List<CommEntity> result = commService.selectCode(vo);

            // then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("selectMenu() 메서드 테스트")
    class SelectMenuTest {

        @Test
        @DisplayName("ADMIN 권한으로 메뉴 조회")
        void selectMenu_asAdmin_success() throws Exception {
            // given
            USER_CODE authlevel = USER_CODE.ADMIN;

            CommEntity menu1 = new CommEntity();
            menu1.setMenucd("M001");
            menu1.setMenunm("관리자 메뉴");
            menu1.setAuthlevel(USER_CODE.ADMIN);

            CommEntity menu2 = new CommEntity();
            menu2.setMenucd("M002");
            menu2.setMenunm("일반 메뉴");
            menu2.setAuthlevel(USER_CODE.NORMAL);

            List<CommEntity> allMenus = Arrays.asList(menu1, menu2);

            when(commDao.selectAllMenu()).thenReturn(allMenus);

            // when
            List<CommEntity> result = commService.selectMenu(authlevel);

            // then
            assertNotNull(result);
            verify(commDao).selectAllMenu();
        }

        @Test
        @DisplayName("NORMAL 권한으로 메뉴 조회")
        void selectMenu_asNormal_success() throws Exception {
            // given
            USER_CODE authlevel = USER_CODE.NORMAL;

            CommEntity menu1 = new CommEntity();
            menu1.setMenucd("M001");
            menu1.setMenunm("관리자 메뉴");
            menu1.setAuthlevel(USER_CODE.ADMIN);

            CommEntity menu2 = new CommEntity();
            menu2.setMenucd("M002");
            menu2.setMenunm("일반 메뉴");
            menu2.setAuthlevel(USER_CODE.NORMAL);

            List<CommEntity> allMenus = Arrays.asList(menu1, menu2);

            when(commDao.selectAllMenu()).thenReturn(allMenus);

            // when
            List<CommEntity> result = commService.selectMenu(authlevel);

            // then
            assertNotNull(result);
            verify(commDao).selectAllMenu();
        }
    }

    @Nested
    @DisplayName("insertMenu() 메서드 테스트")
    class InsertMenuTest {

        @Test
        @DisplayName("메뉴 등록 성공")
        void insertMenu_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setMenucd("M003");
            commVo.setMenunm("새 메뉴");

            USER_CODE authlevel = USER_CODE.ADMIN;

            List<CommEntity> menuList = Collections.singletonList(commVo);

            when(commDao.insertMenu(any(CommEntity.class))).thenReturn(1);
            when(commDao.selectMenu(any(CommEntity.class))).thenReturn(menuList);

            // when
            List<CommEntity> result = commService.insertMenu(commVo, TEST_USER, authlevel);

            // then
            assertNotNull(result);
            verify(commDao).insertMenu(any(CommEntity.class));
            verify(commDao).selectMenu(any(CommEntity.class));
        }

        @Test
        @DisplayName("메뉴 등록 실패 - 등록 안됨")
        void insertMenu_failure() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setMenucd("M003");
            commVo.setMenunm("새 메뉴");

            USER_CODE authlevel = USER_CODE.ADMIN;

            when(commDao.insertMenu(any(CommEntity.class))).thenReturn(0);

            // when
            List<CommEntity> result = commService.insertMenu(commVo, TEST_USER, authlevel);

            // then
            assertNull(result);
            verify(commDao).insertMenu(any(CommEntity.class));
            verify(commDao, never()).selectMenu(any(CommEntity.class));
        }
    }

    @Nested
    @DisplayName("updateMenu() 메서드 테스트")
    class UpdateMenuTest {

        @Test
        @DisplayName("메뉴 수정 성공")
        void updateMenu_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setMenucd("M001");
            commVo.setMenunm("수정된 메뉴");

            USER_CODE authlevel = USER_CODE.ADMIN;

            List<CommEntity> menuList = Collections.singletonList(commVo);

            when(commDao.updateMenu(any(CommEntity.class))).thenReturn(1);
            when(commDao.selectMenu(any(CommEntity.class))).thenReturn(menuList);

            // when
            List<CommEntity> result = commService.updateMenu(commVo, TEST_USER, authlevel);

            // then
            assertNotNull(result);
            verify(commDao).updateMenu(any(CommEntity.class));
            verify(commDao).selectMenu(any(CommEntity.class));
        }

        @Test
        @DisplayName("메뉴 수정 실패 - 수정 안됨")
        void updateMenu_failure() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setMenucd("INVALID");
            commVo.setMenunm("수정된 메뉴");

            USER_CODE authlevel = USER_CODE.ADMIN;

            when(commDao.updateMenu(any(CommEntity.class))).thenReturn(0);

            // when
            List<CommEntity> result = commService.updateMenu(commVo, TEST_USER, authlevel);

            // then
            assertNull(result);
            verify(commDao).updateMenu(any(CommEntity.class));
            verify(commDao, never()).selectMenu(any(CommEntity.class));
        }
    }

    @Nested
    @DisplayName("deleteMenu() 메서드 테스트")
    class DeleteMenuTest {

        @Test
        @DisplayName("메뉴 삭제 성공")
        void deleteMenu_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setMenucd("M001");

            USER_CODE authlevel = USER_CODE.ADMIN;

            List<CommEntity> remainingMenus = Collections.emptyList();

            when(commDao.selectMenu(any(CommEntity.class))).thenReturn(remainingMenus);

            // when
            List<CommEntity> result = commService.deleteMenu(commVo, authlevel);

            // then
            assertNotNull(result);
            verify(commDao).deleteMenu(commVo);
            verify(commDao).selectMenu(any(CommEntity.class));
        }
    }

    @Nested
    @DisplayName("selectCodeGrp() 메서드 테스트")
    class SelectCodeGrpTest {

        @Test
        @DisplayName("코드 그룹 조회 성공")
        void selectCodeGrp_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();

            CommEntity grp1 = new CommEntity();
            grp1.setCodegrp("GRP01");
            grp1.setCodegrpnm("그룹1");

            CommEntity grp2 = new CommEntity();
            grp2.setCodegrp("GRP02");
            grp2.setCodegrpnm("그룹2");

            List<CommEntity> list = Arrays.asList(grp1, grp2);

            when(commDao.selectCodeGrp(any(CommEntity.class))).thenReturn(list);

            // when
            List<CommEntity> result = commService.selectCodeGrp(commVo);

            // then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(commDao).selectCodeGrp(commVo);
        }
    }

    @Nested
    @DisplayName("insertCode() 메서드 테스트")
    class InsertCodeTest {

        @Test
        @DisplayName("코드 등록 성공")
        void insertCode_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setCodegrp("GRP01");
            commVo.setCodecd("003");
            commVo.setCodenm("새 코드");

            List<CommEntity> codeList = Collections.singletonList(commVo);

            when(commDao.selectCode(any(CommEntity.class))).thenReturn(codeList);

            // when
            List<CommEntity> result = commService.insertCode(commVo, TEST_USER);

            // then
            assertNotNull(result);
            verify(commDao).insertCode(any(CommEntity.class));
            verify(commDao).selectCode(any(CommEntity.class));
        }
    }

    @Nested
    @DisplayName("deleteCode() 메서드 테스트")
    class DeleteCodeTest {

        @Test
        @DisplayName("코드 삭제 성공")
        void deleteCode_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setCodegrp("GRP01");
            commVo.setCodecd("001");

            List<CommEntity> remainingCodes = Collections.emptyList();

            when(commDao.selectCode(any(CommEntity.class))).thenReturn(remainingCodes);

            // when
            List<CommEntity> result = commService.deleteCode(commVo);

            // then
            assertNotNull(result);
            verify(commDao).deleteCode(commVo);
            verify(commDao).selectCode(any(CommEntity.class));
        }
    }

    @Nested
    @DisplayName("updateCode() 메서드 테스트")
    class UpdateCodeTest {

        @Test
        @DisplayName("코드 수정 성공")
        void updateCode_success() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setCodegrp("GRP01");
            commVo.setCodecd("001");
            commVo.setCodenm("수정된 코드");

            when(commDao.updateCode(any(CommEntity.class))).thenReturn(1);

            // when
            int result = commService.updateCode(commVo);

            // then
            assertEquals(1, result);
            verify(commDao).updateCode(commVo);
        }

        @Test
        @DisplayName("코드 수정 - 대상 없음")
        void updateCode_noTarget() throws Exception {
            // given
            CommEntity commVo = new CommEntity();
            commVo.setCodegrp("INVALID");
            commVo.setCodecd("999");

            when(commDao.updateCode(any(CommEntity.class))).thenReturn(0);

            // when
            int result = commService.updateCode(commVo);

            // then
            assertEquals(0, result);
        }
    }
}
