//package com.fline.yztb.mgmt.service;
//
//import com.fline.yztb.access.model.Position;
//import com.fline.yztb.access.model.Role;
//import com.fline.yztb.access.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//@ContextConfiguration(locations = { "classpath:applicationContext-server-index.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
//public class DataCombineTest {
//        @Autowired
//        private PositionMgmtService positionMgmtService;
//        @Autowired
//        private  UserMgmtService userMgmtService;
//        @Autowired
//        private RoleMgmtService roleMgmtService;
//
//
//    @Test
//    public void testUserCombine() throws InterruptedException {
//        /**
//         * 将c_user表中数据字段 CERT_NO相同的数据进行合并
//         * 1 岗位方面新增一个合并岗位获取两者之间的并集数据
//         * 2 角色方面的 sql查询是否有 "无证明城市盖章角色"的信息 进行手工合并即可
//         * 3 删除两者之间其中一个数据对应的角色为（"窗口证件查询角色"）的用户信息
//         */
//        List<User>userList=userMgmtService.findrepeateCertNo();
//        for (int i = 0; i < userList.size()-1; i++) {
//            try {
//                int positionflag = 0;
//                User user1 = userList.get(i);
//                User user2 = userList.get(++i);
//
//                Position position1 = positionMgmtService.findById(user1.getPositionId());
//                Position position2 = positionMgmtService.findById(user2.getPositionId());
//
//                List<Role> role1s = roleMgmtService.findByUserRole(user1.getId());
//                List<Role> role2s = roleMgmtService.findByUserRole(user2.getId());
//
//                Position newPosition = new Position();
//                newPosition.setActive(true);
//                newPosition.setType(1);
//                List<String> positionAlist = positionMgmtService.findItems(position1.getId());
//                List<String> positionBlist = positionMgmtService.findItems(position2.getId());
//                Set<String> set = new HashSet<>(positionAlist);
//                set.addAll(positionBlist);
//                List<String> positionClist = new ArrayList<>(set);
//                String[] itemCode = new String[positionClist.size()];
//                positionClist.toArray(itemCode);
//                    newPosition.setName(position1.getName()+"&"+position2.getName()+"(合并)");
//                if (position1.getName().contains("无证明")) {
//                    positionflag++;
//
//                    newPosition.setDepartmentId(position1.getDepartmentId());
//                } else if (position2.getName().contains("无证明")) {
//                    positionflag++;
//
//                    newPosition.setDepartmentId(position2.getDepartmentId());
//                } else {
//                    System.out.println("99999999999999999999未找到无证明用户   " + user1.getName() + "    " + user2.getName());
//                    continue;
//                }
//                if (positionflag == 2) {
//                    System.out.println("99999999999999999999两个无证明用户   " + user1.getName() + "    " + user2.getName());
//
//                } else {
//                    //TODO  如果依据岗位名称查找到的数据有数据则用查找到的数据
//                    Map<String,Object>param=new HashMap<>();
//                    param.put("searchName",newPosition.getName());
//                    param.put("_maxResult",0);
//                    List<Position>positions=positionMgmtService.findList(param);
//                    if(positions!=null&&positions.size()>0){
//                     newPosition=positions.get(0);
//                    }else {
//                        positionMgmtService.create(newPosition, itemCode);
//                    }
//                    //删除"角色为窗口证件查询角色的"信息
//                    if (role1s.size() > 1 || role2s.size() > 1) {
//                        System.out.println("99999999999999999999用户其中有角色大于1   " + user1.getName() + "    " + user2.getName());
//
//                    } else {
//                        Role role1 = role1s.get(0);
//                        Role role2 = role2s.get(0);
//                        int roleflag = 0;
//                        if (role1.getName().equals("窗口证件查询角色")) {
//                            roleflag += 1;
//                        } else if (role2.getName().equals("窗口证件查询角色")) {
//                            roleflag += 2;
//                        }
//
//                        if (roleflag == 3) {
//                            System.out.println("99999999999999999999用户有两个窗口证件查询角色   " + user1.getName() + "    " + user2.getName());
//
//                        } else if (roleflag == 2) {
//                            userMgmtService.remove(user2);
//                            user1.setPositionId(newPosition.getId());
//                            userMgmtService.update(user1);
//                        } else if (roleflag == 1) {
//                            userMgmtService.remove(user1);
//                            user2.setPositionId(newPosition.getId());
//                            userMgmtService.update(user2);
//                        } else {
//                            System.out.println("99999999999999999999用户角色无窗口证件查询角色   " + user1.getName() + "    " + user2.getName());
//
//                        }
//
//                    }
//
//                }
//
//            }catch (Exception e){
//                System.out.println(e.getMessage()+"-----------------------------------------");
//            }
//        }
//
//    }
//}
