momgodb 分页的方式
        1.skip  limit
        通过skip和limit方法可以简单的实现分页操作，但是如果数据量特别巨大的时候，会出现性能的问题，建议不使用！
        2原生的分页
       //分页条件
       of页数好像是从0开始的
           //Pageable pageable = new PageRequest(pageNUmber,pageSize);
           Pageable pageable = PageRequest.of(pageNUmber,pageSize);
            PageRequest of = PageRequest.of(1, 1);
            Query query = new Query();
            query.addCriteria(Criteria.where("price").lt(40));
            query.with(of);
            List<Book> objects = mongoTemplate.find(query, Book.class);
           
        